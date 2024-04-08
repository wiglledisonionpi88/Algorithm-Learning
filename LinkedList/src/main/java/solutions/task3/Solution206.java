package solutions.task3;

import solutions.base.ListNode;

/**
 * 206. 反转链表
 */
public class Solution206 {
    public ListNode reverseList(ListNode head) {
        if (head == null) return null;

        ListNode dummy = new ListNode(0, head);
        ListNode cur = head.next;
        head.next = null;
        while (cur != null) {
            ListNode tmp = cur.next;
            cur.next = dummy.next;
            dummy.next = cur;
            cur = tmp;
        }
        return dummy.next;
    }
}
