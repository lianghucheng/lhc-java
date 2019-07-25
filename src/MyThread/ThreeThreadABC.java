package MyThread;

/**
 * 三线程打印依次10ABC
 * @author lianghucheng
 *
 */
public class ThreeThreadABC implements Runnable{
    private String name;
    private Object prev;
    private Object self;

    private ThreeThreadABC(String name,Object prev,Object self){
        this.name = name;
        this.prev=prev;
        this.self=self;
    }

    @Override
    public void run() {
        int count = 10;
        while(count>0){
            synchronized (prev){
                synchronized (self){
                    System.out.print(name);
                    count--;

                    self.notify();
                }

                try {
                    prev.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){
        Object a=new Object();
        Object b=new Object();
        Object c=new Object();

        ThreeThreadABC pa=new ThreeThreadABC("A",c,a);
        ThreeThreadABC pb=new ThreeThreadABC("B",a,b);
        ThreeThreadABC pc=new ThreeThreadABC("C",b,c);

        try {
            new Thread(pa).start();
            Thread.sleep(1000);
            new Thread(pb).start();
            Thread.sleep(1000);
            new Thread(pc).start();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
