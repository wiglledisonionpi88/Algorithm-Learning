package task1to40.task04;

/**
 * 707. 设计链表
 */
class Node1 {
    int val;
    Node1 next;

    public Node1() {
    }

    public Node1(int val) {
        this.val = val;
        next = null;
    }

    public Node1(int val, Node1 next) {
        this.val = val;
        this.next = next;
    }
}
public class MyLinkedList1 {
    int size;
    Node1 dummy;

    public MyLinkedList1() {
        size = 0;
        dummy = new Node1();
    }

    public int get(int index) {
        if (index > size - 1) return -1;

        Node1 node1 = dummy.next;
        for (int i = 0; i < index; i++) {
            node1 = node1.next;
        }
        return node1.val;
    }

    public void addAtHead(int val) {
        dummy.next = new Node1(val, dummy.next);
        size++;
    }

    public void addAtTail(int val) {
        Node1 node1 = dummy;
        while (node1.next != null) {
            node1 = node1.next;
        }
        node1.next = new Node1(val);
        size++;
    }

    public void addAtIndex(int index, int val) {
        if (index > size) return;
        if (index == size) {
            addAtTail(val);
            return;
        }

        Node1 node1 = dummy;
        for (int i = 0; i < index; i++) {
            node1 = node1.next;
        }
        node1.next = new Node1(val, node1.next);
        size++;
    }

    public void deleteAtIndex(int index) {
        if (index > size - 1) return;

        Node1 node1 = dummy;
        for (int i = 0; i < index; i++) {
            node1 = node1.next;
        }
        node1.next = node1.next.next;
        size--;
    }

    public static void main(String[] args) {
        //        ["MyLinkedList1","addAtHead","addAtTail","addAtIndex","get","deleteAtIndex","get"]
//        [[],[1],[3],[1,2],[1],[1],[1]]
        MyLinkedList1 myLinkedList1 = new MyLinkedList1();
        myLinkedList1.addAtHead(1);
        myLinkedList1.addAtTail(3);
        myLinkedList1.addAtIndex(1, 2);
        myLinkedList1.get(1);
        myLinkedList1.deleteAtIndex(1);
        myLinkedList1.get(1);

//        ["MyLinkedList1","addAtHead","addAtHead","addAtHead","addAtIndex","deleteAtIndex","addAtHead","addAtTail","get","addAtHead","addAtIndex","addAtHead"]
//        [[],[7],[2],[1],[3,0],[2],[6],[4],[4],[4],[5,0],[6]]
//        MyLinkedList1 list = new MyLinkedList1();
//        list.addAtHead(7);
//        list.addAtHead(2);
//        list.addAtHead(1);
//        list.addAtIndex(3, 0);
//        list.deleteAtIndex(2);
//        list.addAtHead(6);
//        list.addAtTail(4);
//        list.get(4);
//        list.addAtHead(4);
//        list.addAtIndex(5, 0);
//        list.addAtHead(6);
    }
}
