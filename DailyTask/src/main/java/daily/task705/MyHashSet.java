package daily.task705;

/**
 * 705. 设计哈希集合
 */
public class MyHashSet {
    int[] bucket = new int[31330];

    public MyHashSet() {

    }

    public void add(int key) {
        bucket[key / 32] |= 1 << (key & 0b11111);
    }

    public void remove(int key) {
        bucket[key / 32] &= ~(1 << (key & 0b11111));
    }

    public boolean contains(int key) {
        return (bucket[key / 32] & (1 << (key & 0b11111))) != 0;
    }

    public static void main(String[] args) {
        MyHashSet myHashSet = new MyHashSet();
        myHashSet.add(1);      // set = [1]
        myHashSet.add(2);      // set = [1, 2]
        myHashSet.contains(1); // 返回 True
        myHashSet.contains(3); // 返回 False ，（未找到）
        myHashSet.add(2);      // set = [1, 2]
        myHashSet.contains(2); // 返回 True
        myHashSet.remove(2);   // set = [1]
        myHashSet.contains(2); // 返回 False ，（已移除）
    }
}

/**
 * Your MyHashSet object will be instantiated and called as such:
 * MyHashSet obj = new MyHashSet();
 * obj.add(key);
 * obj.remove(key);
 * boolean param_3 = obj.contains(key);
 */