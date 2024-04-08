package solutions.task5;

import solutions.base.ListNode;

    fast = fast.next;
public class Solution19 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode slow = dummy, fast = dummy;
        for (int i = 0; i < n; i++) {
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        if (slow.next != null) {
            slow.next = slow.next.next;
        }

        return dummy.next;
    }
}
