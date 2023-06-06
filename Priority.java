import java.util.List;

public class Priority implements Algorithm {
	private List<Task> list;

	public Priority(List<Task> queue) {
		list = queue;
	}

	@Override
	public void schedule() {
		// While the list is not empty
		while (list.size() > 0)
		{
			// Select a task based on Priority scheduling
			Task task = pickNextTask();
			// When a task is selected from the scheduler, the task runs for its entire CPU burst
			CPU.run(task, task.getBurst());
			System.out.println("Task " + task.getName() + " finished.@@@@@@@@@@@@@@@@@@@@@\n");
		}
	}

	// Priority scheduling picks the next task based on priority
	// in this case a low number is considered a higher priority
	@Override
	public Task pickNextTask() {
		// cursor variable
		int index = 0;

		// variable to store the index of the task with the highest priority
		// initiliaze it to the first task's CPU burst
		int index_highest = 0;
		int highest = list.get(0).getPriority();
		Task highestTask = list.get(0);

		// for every task inside the queue:
		for (Task task : list)
		{
			// if the current task's priority is more than the saved priority
			if (task.getPriority() > highest)
			{
				// the current task prio is the new highest
				highest = task.getPriority();
				index_highest = index;

				highestTask = list.get(index_highest);
			}

			index ++;
		}

		// the highest priority task was found, remove it from the queue
		list.remove(index_highest);
		return highestTask;
	}
}
