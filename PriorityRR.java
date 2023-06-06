import java.util.ArrayList;
import java.util.List;

public class PriorityRR implements Algorithm {
	private List<Task> list;
	private Task task;
	private final int SLICE = 10;
	// priority counter that starts at 10 because it is the max level priority
	private int target_priority;

	// a subqueue that will only hold tasks that have the same priority as the counter
	private ArrayList<Task> sublist= new ArrayList<Task>();

	public PriorityRR(List<Task> queue) {
		list = queue;
		target_priority = 10;
	}

	@Override
	public void schedule() {
		// Create a sublist based on the priority level
		sublist = fillSubList();

		// Retreive the first task in the sublist
		task = pickNextTask();

		// While there are still priority levels to proccess
		while (target_priority > -1)
		{
			// There is the possibility that a task is the only task within a priority level
			// Simply run the entire task's CPU burst if that is the case
			if (sublist.size() == 1)
			{
				CPU.run(task, task.getBurst());
				System.out.println("Task " + task.getName() + " finished.@@@@@@@@@@@@@@@@@@@@@\n");
				sublist.remove(0);
			}

			// The sublist has more than 1 element, this means that the sublist must
			// go through RR scheduling
			// While the sublist is not empty
			while (sublist.size() > 0)
			{
				// Check if the task is able to finish within the time slice
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
					sublist.remove(0);
					sublist.add(task);
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
					sublist.remove(0);
				}

				task = pickNextTask();
			}
			
			// At this point in code, the sublist is empty
			// Create the next priority level sublist
			sublist = fillSubList();

			// Retreive the next task in the sublist
			task = pickNextTask();
		}
	}

	// Retreive the first element of the sublist
	@Override
	public Task pickNextTask() {

		// Return null if there are no more tasks
		if (sublist.isEmpty())
		{
			return null;
		}

		Task temp = sublist.get(0);
		return temp;
	}

	// This method creates a sublist and fills it with tasks with the target priority
	private ArrayList<Task> fillSubList() {
		// reset sublist
		sublist.clear();

		// start loading the sublist with tasks
		// iterate through the entire queue, removing any task with the target priority
		// and adding them into the sublist
		while (true)
		{
			for (Task temp : list)
			{
				if (temp.getPriority() == target_priority)
				{
					// add to the sublist
					sublist.add(temp);
				}
			}

			// There is the possibility that an empty sublist is created
			// So an exit conditionn is created where a sublist with at least 1 element must be made
			if (!sublist.isEmpty())
			{
				// Exit the loop because the sublist created is not empty
				target_priority--;
				break;
			}
			else 
			{	
				// The sublist created is empty, this means a new priority level can be searched
				// Must make sure control value does not step out of bounds
				if (target_priority < 0)
				{
					break;
				}

				target_priority--;

			}
		}

		return sublist;
	}

}
