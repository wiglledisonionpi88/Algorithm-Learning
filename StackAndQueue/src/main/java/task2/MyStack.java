package task2;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 225. 用队列实现栈
 */
public class MyStack {
    private Queue<Integer> inQueue = new ArrayDeque<>();
    private Queue<Integer> outQueue = new ArrayDeque<>();

    public MyStack() {

    }

    public void push(int x) {
        inQueue.offer(x);
    }

    public int pop() {
        preOutStack();
        return outQueue.poll();
    }

    public int top() {
        preOutStack();
        Integer res = outQueue.poll();
        inQueue.offer(res);
        return res;
    }

    private void preOutStack() {
        while (inQueue.size() > 1) {
            outQueue.offer(inQueue.poll());
        }
        Queue<Integer> tmp = inQueue;
        inQueue = outQueue;
        outQueue = tmp;
    }

    public boolean empty() {
        return inQueue.isEmpty();
    }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
