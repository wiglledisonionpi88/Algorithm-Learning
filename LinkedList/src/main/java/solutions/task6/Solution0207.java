package solutions.task6;

import solutions.base.ListNode;

public class Solution0207 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode posA = headA, posB = headB;
        while (posA != posB) {
            posA = posA == null ? headB : posA.next;
            posB = posB == null ? headA : posB.next;
        }
        return posA;
    }
}
