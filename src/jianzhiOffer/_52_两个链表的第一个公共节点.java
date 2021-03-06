package jianzhiOffer;

/**
 * 两个链表的第一个公共节点
 * 输入两个链表，找出它们的第一个公共节点
 * 需要注意的是单链表可能有环，可能无环，可能相交，也可能不相交
 * 1）如何判断两个单链表有环还是无环，利用hashset，对节点进行存储，然后
 * 发现重复节点即为入口，如果不用哈希表，可以使用一个慢指针一次走一步，和一个
 * 快指针一次两步，如果快走到null则无环。如果有环，则快指针和慢指针就会相遇，
 * 然后快指针回到头节点，此时快指针和慢指针同时走一定会在环的入口相遇。
 * 两个链表都无环的情况下，把head1所有节点放入hashmap中，然后遍历head2看
 * 是否已经put过了，如果有则为第一个相交节点，否则返回false。如果不用hashmap，
 * 遍历head1链表记录长度和最后一个节点，len1和end1，遍历head2链表，记录长度和
 * 最后一个节点，len2和end2，判断end1和end2的内存地址是否相等，如果不等，则不相交
 * 如果相等则相交但是不是第一个相交的点，如果len1和len2的长度不等，且差值为H则让长
 * 的那个先走H步，然后再同时走到第一个相交节点。
 * 2)如果一个有环一个无环则不可能相交
 * 3)如果两个链表都有环，此时分三种情况讨论
 */
public class _52_两个链表的第一个公共节点 {


    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }
    /**
     * 如果说明没有环的情况
     */
    public Node getIntersectionNode(Node headA, Node headB) {
        int length1 = 0, length2 = 0;
        Node temp1 = headA;
        while(temp1 != null){
            length1++;
            temp1 = temp1.next;
        }
        Node temp2 = headB;
        while(temp2 != null){
            length2++;
            temp2 = temp2.next;
        }
        temp1 = headA;
        temp2 = headB;
        int maxLength = Math.max(length2,length1);
        int abs = Math.abs(length1 - length2);
        if(maxLength == length1){
            while(abs-->0){
                temp1 = temp1.next;
            }
        }
        else{
            while(abs-->0){
                temp2 = temp2.next;
            }
        }
        while(temp1 != null && temp2 != null){
            if(temp1 == temp2){
                return temp1;
            }
            temp2 = temp2.next;
            temp1 = temp1.next;
        }
        return null;
    }

    /**
     *需要先判环
     */
    public static Node getIntersectNode(Node headA, Node headB) {
        if (headA == null || headB == null) {
            return null;
        }
        Node loop1 = getLoopNode(headA);
        Node loop2 = getLoopNode(headB);
        if (loop1 == null && loop2 == null) {
            return noLoop(headA, headB);
        }
        if (loop1 != null && loop2 != null) {
            return bothLoop(headA, loop1, headB, loop2);
        }
        return null;
    }

    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node n1 = head.next; // n1 -> slow
        Node n2 = head.next.next; // n2 -> fast
        while (n1 != n2) {
            if (n2.next == null || n2.next.next == null) {
                return null;
            }
            n2 = n2.next.next;
            n1 = n1.next;
        }
        n2 = head; // n2 -> walk again from head
        while (n1 != n2) {
            n1 = n1.next;
            n2 = n2.next;
        }
        return n1;
    }

    public static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node cur1 = head1;
        Node cur2 = head2;
        int n = 0;
        while (cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }
        if (cur1 != cur2) {
            return null;
        }
        cur1 = n > 0 ? head1 : head2;
        cur2 = cur1 == head1 ? head2 : head1;
        n = Math.abs(n);
        while (n != 0) {
            n--;
            cur1 = cur1.next;
        }
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        Node cur1 = null;
        Node cur2 = null;
        if (loop1 == loop2) {
            cur1 = head1;
            cur2 = head2;
            int n = 0;
            while (cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }
            while (cur2 != loop2) {
                n--;
                cur2 = cur2.next;
            }
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n != 0) {
                n--;
                cur1 = cur1.next;
            }
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        } else {
            cur1 = loop1.next;
            while (cur1 != loop1) {
                if (cur1 == loop2) {
                    return loop1;
                }
                cur1 = cur1.next;
            }
            return null;
        }
    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

    }
}
