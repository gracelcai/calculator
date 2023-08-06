import java.util.Scanner;
import java.util.Stack;

class Example {
    class TreeNode {
        int weight;
        String str;
        TreeNode left, right;
        public TreeNode(int weight, String str) {
            this.weight = weight;
            this.str = str;
        }
    }

    public int calculate(String s) {
        if (s == null || s.length() == 0) return 0;
        TreeNode root = buildTree(s); // build expression tree
        return (int)evaluate(root); // post-order traversal of the tree
    }

    // build tree based on input string, min-tree. return root
    private TreeNode buildTree(String s) {
        int n = s.length();
        char[] chars = s.trim().toCharArray();
        Stack<TreeNode> stack = new Stack<>();
        int base = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < n; i++) {
            char c = chars[i];
            if (c == ' ') {
                continue;
            } else if (c == '(') { // '()' are used to add weight, not stored in tree
                base = getWeight(base, c);
                continue;
            } else if (c == ')') {
                base = getWeight(base, c);
                continue;
            } else if (i < n - 1 && isDigit(chars[i]) && isDigit(chars[i + 1])) { // continue to get remaining of the int
                sb.append(c);
                continue;
            }
            String str;
            if (isDigit(c)) {
                sb.append(c);
                str = sb.toString();
                sb.setLength(0); // clean up
            } else {
                str = c + "";
            }

            // use monotonous stack to build min-tree
            TreeNode node = new TreeNode(getWeight(base, c), str);
            while (!stack.isEmpty() && node.weight <= stack.peek().weight) {
                node.left = stack.pop();
            }
            if (!stack.isEmpty()) {
                stack.peek().right = node;
            }
            stack.push(node);

        }
        TreeNode root = null;
        while (!stack.isEmpty()) {
            root = stack.pop();
        }

        return root; // it's the root of tree, always a operator
    }

    // post-order traversal to evaluate the expression
    private long evaluate(TreeNode root) {
        if (root == null) return 0;
        long left = evaluate(root.left);
        long right = evaluate(root.right);
        long result = 0;
        switch(root.str) {
            case "+":
                result = left + right;
                break;
            case "-":
                result = left - right;
                break;
            case "*":
                result = left * right;
                break;
            case "/":
                result = left / right;
                break;
            default:
                result = Long.parseLong(root.str);
        }
        return result;
    }

    // get weight of the character. integer weights the most and will be leaf.
    // Remember to store the result using long
    private int getWeight(int base, char c) {
        if (c == '(') return base + 10;
        if (c == ')') return base - 10;
        if (c == '+' || c == '-') return base + 1;
        if (c == '*' || c == '/') return base + 2;
        return Integer.MAX_VALUE;
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an expression:");
        String input = scanner.next();
        Example example = new Example();
        System.out.println("The answer is " + example.calculate(input));
    }
}

