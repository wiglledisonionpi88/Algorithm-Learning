package task1to40.task03;

import base.ListNode;

public class Solution203 {
    public static void main(String[] args) {

    }

    public ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(0, head);
        ListNode res = dummy;

        while (dummy.next != null) {
            ListNode tmp = dummy.next;
            if (tmp.val == val) {
                dummy.next = tmp.next;
            } else {
                dummy = dummy.next;
            }
        }
        return res.next;
    }
}
