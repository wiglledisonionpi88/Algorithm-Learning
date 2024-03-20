package task04;

class Node {
    int val;
    Node pre;
    Node next;

    public Node() {
    }

    public Node(int val, Node pre, Node next) {
        this.val = val;
        this.pre = pre;
        this.next = next;
    }
}
public class MyLinkedList {
    int size;
    Node headDummy;
    Node tailDummy;

    public MyLinkedList() {
        size = 0;
        headDummy = new Node();
        tailDummy = new Node(0, headDummy, null);
        headDummy.next = tailDummy;
    }

    public int get(int index) {
        Node node = getNodeAt(index);
        return node == null ? -1 : node.val;
    }

    public Node getNodeAt(int index) {
        if (index < 0 || index > size - 1) return null;

        Node cur;
        if (index < size / 2) {
            cur = headDummy.next;
            for (int i = 0; i < index; i++) {
                cur = cur.next;
            }
        } else {
            cur = tailDummy.pre;
            for (int i = 0; i < size - 1 - index; i++) {
                cur = cur.pre;
            }
        }
        return cur;
    }

    public void addAtHead(int val) {
        Node newNode = new Node(val, headDummy, headDummy.next);
        headDummy.next = newNode;
        newNode.next.pre = newNode;
        size++;
    }

    public void addAtTail(int val) {
        Node newNode = new Node(val, tailDummy.pre, tailDummy);
        newNode.pre.next = newNode;
        tailDummy.pre = newNode;
        size++;
    }

    public void addAtIndex(int index, int val) {
        if (index == size) {
            addAtTail(val);
            return;
        }

        Node node = getNodeAt(index);
        if (node == null) return;

        Node newNode = new Node(val, node.pre, node);
        node.pre.next = newNode;
        node.pre = newNode;
        size++;
    }

    public void deleteAtIndex(int index) {
        Node node = getNodeAt(index);
        if (node == null) return;

        node.pre.next = node.next;
        node.next.pre = node.pre;
        size--;
    }

    public static void main(String[] args) {
        //        ["MyLinkedList1","addAtHead","addAtTail","addAtIndex","get","deleteAtIndex","get"]
//        [[],[1],[3],[1,2],[1],[1],[1]]
        MyLinkedList myLinkedList = new MyLinkedList();
        myLinkedList.addAtHead(1);
        myLinkedList.addAtTail(3);
        myLinkedList.addAtIndex(1, 2);
        myLinkedList.get(1);
        myLinkedList.deleteAtIndex(1);
        myLinkedList.get(1);

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
