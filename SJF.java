import java.util.List;

public class SJF implements Algorithm {
	private  List<Task> list;

	public SJF(List<Task> queue) {
		list = queue;
	}

	@Override
	public void schedule() {
		// While the list is not empty
		while (list.size() > 0)
		{
			// Select a task based on SJF scheduling
			Task task = pickNextTask();
			// When a task is selected from the scheduler, the task runs for its entire CPU burst
			CPU.run(task, task.getBurst());
			System.out.println("Task " + task.getName() + " finished.@@@@@@@@@@@@@@@@@@@@@\n");
		}
	}

	// SJF picks the next task based on the shortest CPU burst
	@Override
	public Task pickNextTask() {
		// cursor variable
		int current = 0;
		int index = 0;

		// variables to store the index of the task with the shortest CPU burst
		// initiliaze it to the first task's CPU burst
		int index_smallest = 0;
		int smallest = list.get(0).getBurst();
		Task smallestTask = list.get(0);

		// for every task inside the queue:
		for (Task task : list)
		{
			// assign the current burst
			current = task.getBurst();
			// if current burst is smaller than the smallest logged burst, update it
			if (current < smallest)
			{
				// update the smallest CPU burst found and the index it occured
				smallest = current;
				index_smallest = index;

				smallestTask = list.get(index_smallest);
			}

			index ++;
		}

		// the shortest CPU burst was found, remove it from the queue
		list.remove(index_smallest);
		return smallestTask;
	}
}
