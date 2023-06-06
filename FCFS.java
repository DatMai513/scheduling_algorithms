import java.util.List;

public class FCFS implements Algorithm {
	private List<Task> list;

	public FCFS(List<Task> queue) {
		list = queue;
	}

	@Override
	public void schedule() {
		// While the list is not empty
		while (list.size() > 0)
		{
			// Select a task based on FCFS scheduling
			Task task = pickNextTask();
			// Run the task for its entire CPU burst
			CPU.run(task, task.getBurst());
			System.out.println("Task " + task.getName() + " finished.@@@@@@@@@@@@@@@@@@@@@\n");

			// remove it from the queue
			list.remove(0);
		}
	}

	@Override
	public Task pickNextTask() {
		// for FCFS, the first task in the list is always allocated
		Task temp = list.get(0);
		return temp;
	}
}	
