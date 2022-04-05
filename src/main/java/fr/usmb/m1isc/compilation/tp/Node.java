package fr.usmb.m1isc.compilation.tp;

import java.util.ArrayList;

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

    public void variables(ArrayList<String> list){
        if(type == NodeType.AFFECTATION){
            if(!list.contains(value)) {
                list.add((String) value);
            }
        } else if (type == NodeType.POINT_VIRGULE ||
                type == NodeType.MULT ||
                type == NodeType.MOINS ||
                type == NodeType.PLUS ||
                type == NodeType.DIV){
            fg.variables(list);
            fd.variables(list);
        }
    }

    public String codeAssembly() {
        if (type == NodeType.AFFECTATION) {
            return fd.codeAssembly() + "mov " + fg.toString() + ", eax\n";
        } else if (type == NodeType.ENTIER || type == NodeType.IDENT) {
            return "mov eax, " + value + "\n";
        } else if (type == NodeType.POINT_VIRGULE) {
            return fg.codeAssembly() + fd.codeAssembly();
        } else {
            // System.out.print("t : " + type + " ");
            // c'est un opérateur
            String str = fg.codeAssembly();
            str += "push eax\n";
            str += fd.codeAssembly();
            str += "pop ebx\n";
            if(type == NodeType.PLUS){
                str += "add eax, ebx\n";
            } else if(type == NodeType.MOINS){
                str += "sub ebx, eax\nmov eax, ebx\n";
            } else if(type == NodeType.MULT){
                str += "mul eax, ebx\n";
            } else if(type == NodeType.DIV){
                str += "div ebx, eax\nmov eax, ebx\n";
            } else {
                System.out.println("non operateur");
            }
            return str;
        }
    }
}
