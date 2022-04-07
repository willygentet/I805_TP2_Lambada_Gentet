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
            if(!list.contains(fg.value)) {
                list.add((String) fg.value);
            }
        } else{
            if (fg != null){
                fg.variables(list);
            }
            if (fd != null){
                fd.variables(list);
            }
        }
    }

    public String codeAssembly() {
        String str;
        if (type == NodeType.AFFECTATION) {
            return fd.codeAssembly() + "\tmov " + fg.toString() + ", eax\n";
        } else if (type == NodeType.ENTIER || type == NodeType.IDENT) {
            return "\tmov eax, " + value + "\n";
        } else if (type == NodeType.POINT_VIRGULE) {
            return fg.codeAssembly() + fd.codeAssembly();
        } else if (type == NodeType.INPUT) {
            return "\tin eax\n";
        } else if (type == NodeType.OUTPUT) {
            return fg.codeAssembly() + "\tout eax\n";
        } else if(type == NodeType.MOD) {
            str = "";
            str += fd.codeAssembly();
            str += "\tpush eax\n";
            str += fg.codeAssembly();
            str += "\tpop ebx\n";
            str += "\tmov ecx, eax\n\tdiv ecx, ebx\n";
            str += "\tmul ecx, ebx\n\tsub eax, ecx\n";
            return str;
        } else if(type == NodeType.LT) {
            str = "";
            str += fg.codeAssembly();
            str += "\tpush eax\n";
            str += fd.codeAssembly();
            str += "\tpop ebx\n";
            str += "\tsub eax, ebx\n";
            str += "\tjle faux_gt_1\n";
            str += "\tmov eax, 1\n";
            str += "\tjmp sortie_gt_1\n";
            str += "faux_gt_1:\n";
            str += "\tmov eax, 0\n";
            str += "sortie_gt_1:\n";
            return str;
        } else if(type == NodeType.WHILE) {
            str = "";
            str += "debut_while_1:\n";
            str += fg.codeAssembly();
            str += "\tjz sortie_while_1\n";
            str += fd.codeAssembly();
            str += "\tjmp debut_while_1\n";
            str += "sortie_while_1:\n";
            return str;
        } else {
            System.out.print("t : " + type + " ");
            // c'est un opérateur
            str = fg.codeAssembly();
            str += "\tpush eax\n";
            str += fd.codeAssembly();
            str += "\tpop ebx\n";
            if(type == NodeType.PLUS){
                str += "\tadd eax, ebx\n";
            } else if(type == NodeType.MOINS){
                str += "\tsub ebx, eax\n\tmov eax, ebx\n";
            } else if(type == NodeType.MULT){
                str += "\tmul eax, ebx\n";
            } else if(type == NodeType.DIV){
                str += "\tdiv ebx, eax\n\tmov eax, ebx\n";
            } else {
                System.out.println("non operateur");
            }
            return str;
        }
    }

    public String codeMachine(){
        String str = "DATA SEGMENT\n";
        ArrayList<String> listVars = new ArrayList<String>();
        variables(listVars);
        for (int i = 0; i < listVars.size(); i++) {
            str += listVars.get(i) + " DD\n";
        }
        str += "DATA ENDS\n";
        str += "CODE SEGMENT\n";
        str += codeAssembly();
        str += "CODE ENDS";
        return str;
    }
}
