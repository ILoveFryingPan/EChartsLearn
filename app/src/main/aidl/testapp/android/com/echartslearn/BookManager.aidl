// BookManager.aidl
package testapp.android.com.echartslearn;

// Declare any non-default types here with import statements
import testapp.android.com.echartslearn.aidl.Book;
interface BookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);
    List<Book> getBooks();
    void addBook(in Book book);
//    void sendMsg(String msg);
}
