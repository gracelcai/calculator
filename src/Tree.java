public class Tree {
    private Node root;

    public Tree() {
        root = null;
    }

    public void add(String value) { //TODO add separate add methods for left and right
        Node node = root;
        while(node != null) {
            if(node.getLeft() == null) {
                node = node.getLeft();
            } else if (node.getRight() == null) {
                node = node.getRight();
            } else {
                node = node.getLeft();
            }
        }

    }
}
