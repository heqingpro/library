package com.ipanel.web.common.system;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.ipanel.webapp.framework.util.Log;

public class SystemInitListener implements ServletContextListener {

	private String TAG = "SystemInitListener";

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {

			// //从思迁获取回看数据
			// startGetDataFromSQTask();
			// //从服务器中获取直播节目数据
			// startGetDataFromServerTask();
			// //系统定时下发直播节目数据
			// startReleaseProgramTask();

		} catch (Exception e) {
			Log.e(TAG, "****init throw exception:" + e);
			System.exit(0);
		}
	}

	// /**
	// * 从思迁获取回看数据
	// *
	// * @throws Exception
	// */
	// private void startGetDataFromSQTask() throws Exception {
	// String cron = PropertyUtil.getPropertyValue("get_data_from_sq_cron");
	// if (cron == null || cron.equals("")){
	// return;
	// }
	// JobDetail jobDetail = new JobDetail("getDataFromSQJob",
	// "group",GetDataFromSQJob.class);
	// // 目标 创建任务计划
	// CronTrigger trigger = new
	// CronTrigger("getDataFromSQJobTrigger","getDataFromSQJob",cron);
	// Scheduler sched = new StdSchedulerFactory().getScheduler();
	// sched.scheduleJob(jobDetail, trigger);
	// sched.start();
	// }
	//
	// /**
	// * 从服务器中获取直播节目数据
	// *
	// * @throws Exception
	// */
	// private void startGetDataFromServerTask() throws Exception {
	// String cron = PropertyUtil.getPropertyValue("get_data_from_server_cron");
	// if (cron == null || cron.equals("")){
	// return;
	// }
	// JobDetail jobDetail = new JobDetail("getDataFromServerJob",
	// "group",GetDataFromServerJob.class);
	// // 目标 创建任务计划
	// CronTrigger trigger = new
	// CronTrigger("getDataFromServerJobTrigger","getDataFromServerJob",cron);
	// Scheduler sched = new StdSchedulerFactory().getScheduler();
	// sched.scheduleJob(jobDetail, trigger);
	// sched.start();
	// }
	//
	// /**
	// * 系统定时下发直播节目数据
	// *
	// * @throws Exception
	// */
	// private void startReleaseProgramTask() throws Exception {
	// String cron = PropertyUtil.getPropertyValue("release_Program_cron");
	// if (cron == null || cron.equals("")){
	// return;
	// }
	// JobDetail jobDetail = new JobDetail("releaseProgramJob",
	// "group",ReleaseProgramJob.class);
	// // 目标 创建任务计划
	// CronTrigger trigger = new
	// CronTrigger("releaseProgramJobTrigger","releaseProgramJob",cron);
	// Scheduler sched = new StdSchedulerFactory().getScheduler();
	// sched.scheduleJob(jobDetail, trigger);
	// sched.start();
	// }

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}
}
