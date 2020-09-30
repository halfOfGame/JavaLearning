package GrabageCollection;

/**
 * @author halfOfGame
 * @create 2020-03-20,9:17
 */

//The object self-rescue
public class FinalizeEscapeGC {

    public static FinalizeEscapeGC SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("yes, I am stiil alive :)");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGC.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGC();

        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);
        if (SAVE_HOOK != null)
            SAVE_HOOK.isAlive();
        else
            System.out.println("no, I am dead :(");

        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);
        if (SAVE_HOOK != null)
            SAVE_HOOK.isAlive();
        else
            System.out.println("no, I am dead :(");
    }

}
