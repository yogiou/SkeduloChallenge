https://github.com/Skedulo/backend-tech-test 

**0.0.2-RELEASE update**

1. Use Date for start and finish instead of String 
2. Use sorted array list instead of priority queue, use binary search instead of linear search to find next performance
3. improve the performance

**How to run? (Assume it is Linux or Mac system)**
1. Install Java 11 or above
2. Install Maven 3.8.1 or above
3. Go to project root directory
4. Run "mvn install"
6. Run command "java -jar ./target/SkeduloChallenge-0.0.2-RELEASE.jar [input file]"

**Thought process behind the solution and the design decisions**

To find out the optimal performance list, the program will use the priority queue
to get the optimal one. The priority queue will sort based on the following priority:
1. performance start time
2. performance priority
3. performance length

When polling the element from priority queue, it will get the just current incoming performance.

First will check if this performance need to update the start time based on last watched performance's
finish time. 

If needed, add the updated performance back to the queue.

If not needed, find the next higher priority and most recent performance which overlapped with current, 
and then if the next performance is found and the finish time is before the current one finish
time, split the current and add it to the priority queue. Finally, add the watched performance to 
the result list.

After all the performance polled from the priority queue, the result list is the output.

**Feedback question**

1. One thing is input file validation as assumed the input json file is valid. Second is the timezone in the same file. I assumed the start and finish time in the same file are in the same timezone.
2. Choosing spring boot framework is because it provided quite a lot of useful functions such as dependency injection. Choosing Kotlin is due to it is null safety and easy to use. And using priority queue as the data structure is because it is useful to find the optimal elements.
3. Due to the tight schedule, it is a little rush for me to finish the test. And for the verification script I think it could be better if you can provide more details instructions on how to use without reading the script to find out.





