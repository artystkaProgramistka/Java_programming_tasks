package zad2;

import static java.nio.file.FileVisitResult.*;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.EnumSet;

public class Visitor extends SimpleFileVisitor<Path> {

    private FileChannel kanalWyjsciowy;
    private ByteBuffer buf;

    Charset kodowanieWejscia  = Charset.forName("Cp1250"),
            kodowanieWyjscia = Charset.forName("UTF-8");

    public Visitor(Path sciezkaDoPlikuWyjsciowego) throws IOException {
        this.kanalWyjsciowy = FileChannel.open(sciezkaDoPlikuWyjsciowego, EnumSet.of(CREATE, APPEND));
    }

    private void zmienKodowanieNaUtf8(FileChannel kanalWejsciowy, long buffer_size){
        buf = ByteBuffer.allocate((int)buffer_size +1);
        buf.clear();

        try {
            kanalWejsciowy.read(buf);
            buf.flip();
            CharBuffer cbuf = kodowanieWejscia.decode(buf);
            ByteBuffer buf = kodowanieWyjscia.encode(cbuf);

            while( buf.hasRemaining() )
                this.kanalWyjsciowy.write(buf);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public FileVisitResult visitFile(Path sciezkaDoPlikuWejsciowego, BasicFileAttributes atrybuty) {
        System.out.format("File: "+sciezkaDoPlikuWejsciowego);
        System.out.println("(" + atrybuty.size() + "bytes)");
        try{
            this.zmienKodowanieNaUtf8(FileChannel.open(sciezkaDoPlikuWejsciowego), atrybuty.size());
        }catch(IOException e){
            e.printStackTrace();
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file_path, IOException e) {
        System.err.println("Visitor.visitFileFailed: " + e);
        return CONTINUE;
    }
}