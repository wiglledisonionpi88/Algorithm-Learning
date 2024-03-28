package task1to40.task05;

import base.ListNode;

/**
 * 206. 反转链表
 */
public class Solution206 {
    public ListNode reverseList(ListNode head) {
        ListNode dummy = new ListNode();
        ListNode cur = head;

        while (cur != null) {
            ListNode tmp = cur.next;
            cur.next = dummy.next;
            dummy.next = cur;
            cur = tmp;
        }
        return dummy.next;
    }
}
