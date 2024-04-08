package solutions.task4;

import solutions.base.ListNode;

/**
 * 24. 两两交换链表中的节点
 */
public class Solution24 {
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0, head);
        ListNode cur = dummy;

        // 有两个节点就开始交换
        while (cur.next != null && cur.next.next != null) {
            ListNode second = cur.next.next;

            cur.next.next = second.next;
            second.next = cur.next;
            cur.next = second;

            cur = cur.next.next;
        }
        return dummy.next;
    }
}
