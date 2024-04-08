package solutions.task2;

/**
 * 707. 设计链表
 */
class Node {
    int val;
    Node next;

    public Node() {
    }

    public Node(int val) {
        this.val = val;
    }

    public Node(int val, Node next) {
        this.val = val;
        this.next = next;
    }
}
public class MyLinkedList {
    private Node dummy;
    private int size = 0;

    public MyLinkedList() {
        dummy = new Node();
    }

    public int get(int index) {
        if (index < 0 || index >= size) return -1;

        Node cur = dummy;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.next.val;
    }

    public void addAtHead(int val) {
        Node node = new Node(val);
        node.next = dummy.next;
        dummy.next = node;
        size++;
    }

    public void addAtTail(int val) {
        Node cur = dummy;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = new Node(val);
        size++;
    }

    public void addAtIndex(int index, int val) {
        if (index < 0 || index > size) {
            return;
        }

        Node cur = dummy;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        Node node = new Node(val);
        node.next = cur.next;
        cur.next = node;
        size++;
    }

    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) return;

        Node cur = dummy;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.next = cur.next.next;
        size--;
    }
}
