package task1to40.task06;

import base.ListNode;

/**
 * 142. 环形链表 II
 */
public class Solution142 {
    public ListNode detectCycle1(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        do {
            if (fast != null && fast.next != null) {
                fast = fast.next.next;
            } else return null;
            if (slow != null) {
                slow = slow.next;
            } else return null;
        } while (fast != slow);

        ListNode node = head;
        while (node != slow) {
            node = node.next;
            slow = slow.next;
        }
        return node;
    }

    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                ListNode p1 = head;
                ListNode p2 = fast;
                while (p1 != p2) {
                    p1 = p1.next;
                    p2 = p2.next;
                }
                return p1;
            }
        }

        return null;
    }
}
