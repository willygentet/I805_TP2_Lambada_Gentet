package fr.usmb.m1isc.compilation.tp;

public class Node {
    private NodeType type;
    private Object value;
    private Node fg, fd;

    public Node(NodeType type, Object value, Node fg, Node fd){
        this.type = type;
        this.value = value;
        this.fg = fg;
        this.fd = fd;
    }
    public Node(NodeType type, Object value){
        this.type = type;
        this.value = value;
        this.fg = null;
        this.fd = null;
    }
    public Node(NodeType type) {
        this.type = type;
        this.value = null;
        this.fg = null;
        this.fd = null;
    }
    public Node(NodeType type, Node fg, Node fd) {
        this.type = type;
        this.value = null;
        this.fg = fg;
        this.fd = fd;
    }


    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Node getFg() {
        return fg;
    }

    public void setFg(Node fg) {
        this.fg = fg;
    }

    public Node getFd() {
        return fd;
    }

    public void setFd(Node fd) {
        this.fd = fd;
    }

    @Override
    public String toString() {
        String string = "";
        if(value != null){
            string += value;
        } else if(fg != null && fd != null) {
            if (type == NodeType.POINT_VIRGULE)
                string += "\n";
            string += "(" + this.type + " " + this.fg + " " + this.fd + ")";
        } else if(fg != null && fd == null) {
                string += this.type + " " + this.fg;
        } else {
            string += this.type;
        }
        return string;
    }
}
