package org.enricogiurin.ocp17.book.ch13;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UsageOfScheduledExecutorService {

  private Runnable runnable = () -> {
    try {
      Thread.sleep(2_000);
      System.out.println("I am done!");
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

  };

  public static void main(String[] args)
      throws ExecutionException, InterruptedException, TimeoutException {
    new UsageOfScheduledExecutorService().scheduleWithFixedDelay();
  }

  //done
  //hello
  void schedule() {
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    try {
      scheduledExecutorService.schedule(runnable, 2L, TimeUnit.SECONDS);
    } finally {
      scheduledExecutorService.shutdown();
    }
    System.out.println("done");
  }

  void scheduleCallable() throws ExecutionException, InterruptedException, TimeoutException {
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    ScheduledFuture<String> future;
    try {
      //we wait (at least) 2s before executing the task
      future = scheduledExecutorService.schedule(() -> "ciao", 2,
          TimeUnit.SECONDS);
    } finally {
      scheduledExecutorService.shutdown();
    }
    String result = future.get(5L, TimeUnit.SECONDS);
    System.out.println(result);
  }

  void scheduleWithFixedDelay() throws InterruptedException {
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
    //scheduleWithFixedDelay accepts only Runnable, not Callable
    try {
      //It takes into account the duration of the task's execution
      scheduledExecutorService.scheduleWithFixedDelay(
          runnable,
          2, 1, TimeUnit.SECONDS);
      Thread.sleep(10_000);
      //after some time we shout it down
    } finally {
      scheduledExecutorService.shutdown();
    }
  }

  //with scheduleAtFixedRate can result in the same action
  // being executed by two threads at the same time
  void scheduleAtFixedRate() throws InterruptedException {
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
    //scheduleWithFixedDelay accepts only Runnable, not Callable
    try {
      //It does not take into account the duration of the task's execution
      scheduledExecutorService.scheduleAtFixedRate(
          runnable,
          2, 1, TimeUnit.SECONDS);
      Thread.sleep(10_000);
      //after some time we shout it down
    } finally {
      scheduledExecutorService.shutdown();
    }
  }

}



