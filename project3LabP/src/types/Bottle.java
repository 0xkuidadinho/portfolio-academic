package types;

import java.util.Stack;
import java.util.Iterator;
import java.util.EmptyStackException;

public class Bottle {
    public static final int DEFAULT_CAPACITY = 5;
    public static final String EMPTY = "⬜";
    public static final String EOL = System.lineSeparator();

    private Stack<Filling> contents;
    private Iterator<Filling> iterator;
    private int capacity;

    public Bottle() {
        this(DEFAULT_CAPACITY);
    }

    public Bottle(int capacity) {
        this.contents = new Stack<>();
        this.capacity = capacity;
    }

    public Bottle(Filling[] content) {
        this.capacity = content.length;
        this.contents = new Stack<>();
        for (Filling fill : content) {
            this.contents.push(fill);
        }
    }

    public boolean isFull() {
        for (Filling fill : contents) {
            if (fill == null) {
                return false;
            }
        }
        return contents.size() >= capacity;
    }

    public boolean isEmpty() {
        return contents.isEmpty();
    }

    public Filling top() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        this.iterator = contents.iterator();
        while (iterator.hasNext()) {
            Filling fill = iterator.next();
            if (fill != null) {
                return fill; 
            }
        }  
        throw new EmptyStackException();
    }

    public int spaceAvailable() {
        int nonNullCount = 0;
        for (Filling fill : contents) {
            if (fill != null) {
                nonNullCount++;
            }
        }
        return capacity - nonNullCount;
    }

    public void pourOut() {
        if (!isEmpty()) {
            Stack<Filling> tempStack = new Stack<>();
            while (contents.size() > 1) {
                tempStack.push(contents.pop());
            }
            contents.pop();
            while (!tempStack.isEmpty()) {
                contents.push(tempStack.pop());
            }
        } else {
            throw new EmptyStackException();
        }
    }

    public boolean receive(Filling s) {
        if (!isFull()) {
            contents.push(s);
            return true;
        }
        return false;
    }

    public int capacity() {
        return capacity;
    }

    public boolean isSingleFilling() {
        if (!isEmpty()) {
            Filling top = top();
            for (Filling fill : contents) {
                if (fill != top) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public Filling[] getContent() {
        return contents.toArray(new Filling[contents.size()]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int dif = capacity - contents.size();
        for(int i = 0; i < dif; i++) {
        	sb.append(EMPTY).append(EOL);
        }
        for(int i = 0; i < contents.size(); i++) {
        	Filling fill = contents.get(i);
        	if(fill!=null) {
        		sb.append(fill);
        	} else {
        		sb.append(EMPTY);
        	}
        	sb.append(EOL);
        }
        
        return sb.toString();
    }
    
    public Iterator<Filling> iterator() {
        return contents.iterator();
    }
    
}