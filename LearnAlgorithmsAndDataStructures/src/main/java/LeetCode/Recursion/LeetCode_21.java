package LeetCode.Recursion;


public class LeetCode_21 {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }


    public static ListNode creatLists(int... nums) {
        ListNode head = new ListNode(0);
        ListNode temp = head;
        for (int i = 0; i < nums.length; i++) {
            temp.next = new ListNode(nums[i]);
            temp = temp.next;
        }
        return head.next;
    }

    //My first submit,
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode l3 = new ListNode(0);
        ListNode rear = l3;
        int l1Val = Integer.MAX_VALUE, l2Val = Integer.MAX_VALUE;
        while (l1 != null || l2 != null) {
            if (l1 == null) l1Val = Integer.MAX_VALUE;
            else l1Val = l1.val;
            if (l2 == null) l2Val = Integer.MAX_VALUE;
            else l2Val = l2.val;
            if (l1Val < l2Val) {
                ListNode temp = new ListNode(l1Val);
                rear.next = temp;
                rear = temp;
                l1 = l1.next;
            } else {
                ListNode temp = new ListNode(l2Val);
                rear.next = temp;
                rear = temp;
                l2 = l2.next;
            }
        }
        return l3.next;
    }


    //The second solution, recursion
    public static ListNode mergeTwoLists_2(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;
        if (l1.val < l2.val){
            l1.next = mergeTwoLists_2(l1.next, l2);
            return l1;
        }

        else{
            l2.next = mergeTwoLists_2(l1, l2.next);
            return l2;
        }

    }

    //The third solution, reuse node
    public static ListNode mergeTwoLists_3(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode temp = head;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                temp.next = l1;
                l1 = l1.next;
            } else {
                temp.next = l2;
                l2 = l2.next;
            }
            temp = temp.next;
        }
        if (l1 == null)
            temp.next = l2;
        else
            temp.next = l1;
        return head.next;
    }


    public static void display(ListNode head) {
        while (head != null) {
            System.out.print(head.val);
            if (head.next != null)
                System.out.print("->");
            head = head.next;
        }
    }

    public static void main(String[] args) {
        ListNode l1 = creatLists(1, 2, 4);
        ListNode l2 = creatLists(1, 3, 4);
        ListNode l3 = mergeTwoLists_2(l1, l2);
        display(l3);
    }
}
