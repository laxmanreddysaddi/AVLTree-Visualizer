class AVLNode {

    int value;
    int height;
    AVLNode left;
    AVLNode right;

    AVLNode(int value){
        this.value = value;
        this.height = 1;
    }
}