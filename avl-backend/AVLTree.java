class AVLTree {

    AVLNode root;
    String lastRotation = "";

    int height(AVLNode n){
        return n == null ? 0 : n.height;
    }

    int getBalance(AVLNode n){
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    AVLNode rightRotate(AVLNode y){

        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    AVLNode leftRotate(AVLNode x){

        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    AVLNode insert(AVLNode node, int value){

        if(node == null)
            return new AVLNode(value);

        if(value < node.value)
            node.left = insert(node.left, value);
        else
            node.right = insert(node.right, value);

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // LL
        if(balance > 1 && value < node.left.value){
            lastRotation = "LL";
            return rightRotate(node);
        }

        // RR
        if(balance < -1 && value > node.right.value){
            lastRotation = "RR";
            return leftRotate(node);
        }

        // LR
        if(balance > 1 && value > node.left.value){
            lastRotation = "LR";
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
        if(balance < -1 && value < node.right.value){
            lastRotation = "RL";
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    void insertValue(int value){
        root = insert(root,value);
    }
}