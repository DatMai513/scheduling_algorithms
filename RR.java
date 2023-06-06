import java.util.List;

public class RR implements Algorithm {
	private List<Task> list;
	private final int SLICE = 10;
	private Task task;

	public RR(List<Task> queue) {
		list = queue;
	}

	@Override
	public void schedule() {	
		// While the list is not empty
		while (list.size() > 0)
		{
			// Pick a task based on RR schedling
			task = pickNextTask();

			// The selected task can only run for a maximum of a time slice (in this case 10)
			if (task.getBurst() > SLICE)
			{
				// The selected task has a CPU burst greater than the time slice
				// this means the task will not be able to finish on this run

				// run it on the CPU for a single time slice
				CPU.run(task, SLICE);

				// update the task's CPU burst since it still has remaining time
				task.setBurst(task.getBurst() - SLICE);

				// re-add the task to the end of the list
				list.remove(0);
				list.add(task);
			}
			else
			{
				// The selected task has a CPU burst less than the time slice
				// this means the task will be able to finish on this run

				// run it on the CPU for the remaining time slice
				CPU.run(task, task.getBurst());
				
				// prompt that the task has finished
				System.out.println("Task " + task.getName() + " finished.@@@@@@@@@@@@@@@@@@@@@\n");

				// remove it from the queue
				list.remove(0);
			}
		}
	}

	// RR scheduling simply picks the first task in the queue
	@Override
	public Task pickNextTask() {
		Task temp = list.get(0);
		return temp;
	}

}
