package file.util;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QuartzUtil {
	/**
	 * 添加一个定时任务，使用默认的任务组名，触发器组名
	 *
	 * @param jobName
	 *            任务名
	 * @param triggerName
	 *            触发器名
	 * @param cls
	 *            需要执行的Job类
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * @param jobDescription
	 *            Job描述，现用于传参
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static void addJob(String jobName, String triggerName, Class cls, String time, Map paramsMap) throws SchedulerException, ParseException {
		// Scheduler sched = sf.getScheduler();
		JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(jobName, JOB_GROUP_NAME).setJobData(new JobDataMap(paramsMap)).build();
		// 任务名，任务组，任务执行类
		// 触发器
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, TRIGGER_GROUP_NAME).withSchedule(CronScheduleBuilder.cronSchedule(time)).build();// 触发器名,触发器组
		Scheduler scheduler = getScheduler();
		scheduler.scheduleJob(jobDetail, trigger);
		if (!scheduler.isShutdown()) {
			scheduler.start();
		}

	}

	/**
	 * 添加一个定时任务
	 *
	 * @param jobName
	 *            任务名
	 * @param jobGroup
	 *            任务组
	 * @param triggerName
	 *            触发器名
	 * @param triggerGroup
	 *            触发器组
	 * @param job
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * @param jobDescription
	 *            Job描述，现用于传参
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static void addJob(String jobName, String jobGroup, String triggerName, String triggerGroup, Class cls, String time, Map paramsMap) throws SchedulerException, ParseException {
		// Scheduler sched = sf.getScheduler();
		JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(jobName, jobGroup).setJobData(new JobDataMap(paramsMap)).build();// 任务名，任务组，任务执行类
		// 触发器
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroup).withSchedule(CronScheduleBuilder.cronSchedule(time)).build();// 触发器名,触发器组
		Scheduler scheduler = getScheduler();
		scheduler.scheduleJob(jobDetail, trigger);
		if (!scheduler.isShutdown()) {
			scheduler.start();
		}
	}

	/**
	 * 修改一个任务的触发时间(使用默认的任务组名，触发器组名)
	 *
	 * @param jobName
	 * @param time
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static void modifyJobTime(String triggerName, String time) throws SchedulerException, ParseException {
		Scheduler sched = sf.getScheduler();
		// 获取triggerkey,即再配置文件中配置的key
		TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, TRIGGER_GROUP_NAME);
		// 获取trigger
		Trigger trigger = sched.getTrigger(triggerKey);
		if (trigger != null) {
			CronTrigger cronTrigger = (CronTrigger) trigger;
			// 构建调度表达式
			CronExpression cronExpression = new CronExpression(time);
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
			// 按新的cronExpression表达式重新构建trigger
			cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder.withMisfireHandlingInstructionDoNothing()).build();
			// 按新的trigger重新设置job执行
			sched.rescheduleJob(triggerKey, cronTrigger);
		}
	}

	/**
	 * 修改一个任务的触发时间
	 *
	 * @param jobName
	 * @param time
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public static void modifyJobTime(String triggerName, String triggerGroup, String time) throws SchedulerException, ParseException {
		Scheduler sched = sf.getScheduler();
		// 获取triggerkey,即再配置文件中配置的key
		TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
		// 获取trigger
		Trigger trigger = sched.getTrigger(triggerKey);
		if (trigger != null) {
			CronTrigger cronTrigger = (CronTrigger) trigger;
			// 构建调度表达式
			CronExpression cronExpression = new CronExpression(time);
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
			// 按新的cronExpression表达式重新构建trigger
			cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder.withMisfireHandlingInstructionDoNothing()).build();
			// 按新的trigger重新设置job执行
			sched.rescheduleJob(triggerKey, cronTrigger);
		}
	}

	/**
	 * 暂停任务
	 *
	 * @throws SchedulerException
	 */
	public static void pause(String jobName, String jobGroup) throws SchedulerException {
		Scheduler sched = sf.getScheduler();
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		sched.pauseJob(jobKey);
	}

	/**
	 * 移除一个任务
	 *
	 * @param triggerKey
	 *            触发器键
	 * @param jobKey
	 *            工作任务键
	 * @throws SchedulerException
	 *             调度器内部执行异常
	 */
	public static void removeJob(String triggerName, String triggerGroup, String jobName, String jobGroup) throws SchedulerException {
		Scheduler scheduler = getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroup);
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		scheduler.pauseTrigger(triggerKey);
		scheduler.unscheduleJob(triggerKey);
		scheduler.deleteJob(jobKey);
	}

	/**
	 * 恢复任务
	 *
	 * @throws SchedulerException
	 */
	public static void resume(String jobName, String jobGroup) throws SchedulerException {
		Scheduler sched = sf.getScheduler();
		JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
		sched.resumeJob(jobKey);

	}

	/**
	 * 恢复所有任务
	 *
	 * @throws SchedulerException
	 */
	public static void resumeAll() throws SchedulerException {
		Scheduler sched = sf.getScheduler();
		sched.resumeAll();
	}

	/**
	 * 关闭调度器
	 *
	 * @param waitForJobsToComplete
	 *            是否等候所有工作被调度完成后再关闭
	 * @throws SchedulerException
	 */
	public static void shutdown(boolean waitForJobsToComplete) throws SchedulerException {
		Scheduler sched = sf.getScheduler();
		sched.shutdown(waitForJobsToComplete);
	}

	/**
	 * 获取调度器
	 *
	 * @return 定时调度器
	 * @throws SchedulerException
	 *             调度器内部执行异常
	 */
	private static Scheduler getScheduler() throws SchedulerException {
		if (sched == null) {
			sched = sf.getScheduler();
			sched.setJobFactory(jobFactory);
			// sched.getListenerManager().addTriggerListener(new
			// QuartzTriggerListener());
			// sched.getListenerManager().addSchedulerListener(new
			// QuartzSchedulerListener(sched));
		}
		return sched;
	}

	//jobFactory 需要重写，并且如果要通过注入的方式拿到服务，这里必须要交给spring管理
	private static JobFactory jobFactory = (JobFactory)SpringHelper.getBean("jobFactory");

	private static Scheduler sched;

	private static SchedulerFactory sf = new StdSchedulerFactory();

	private final static Map<String, QuartzUtil> instanceMap = new ConcurrentHashMap<String, QuartzUtil>();

	private static String JOB_GROUP_NAME = "jobGroup1";

	private static String TRIGGER_GROUP_NAME = "triggerGroup1";

	private final static Logger logger = (Logger) LoggerFactory.getLogger(QuartzUtil.class);

}
