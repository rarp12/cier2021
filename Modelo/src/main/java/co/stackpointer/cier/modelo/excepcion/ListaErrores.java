package co.stackpointer.cier.modelo.excepcion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.ejb.ApplicationException;


@ApplicationException(rollback = true)
public class ListaErrores extends RuntimeException implements List<ErrorGeneral> {

    private List<ErrorGeneral> errores = new ArrayList<ErrorGeneral>();
    
    public void adicionarValidacion(String mensaje){
        this.errores.add(new ErrorValidacion(mensaje));
    }

    public void adicionarValidacion(String mensaje, Object... valores) {
        this.errores.add(new ErrorValidacion(String.format(mensaje, valores)));
    }
    
    public void adicionarErrorIntegridad(String mensaje){
        this.errores.add(new ErrorIntegridad(mensaje));
    }
    
    public void adicionarErrorIntegridad(String mensaje, Object... valores) {
        this.errores.add(new ErrorIntegridad(String.format(mensaje, valores)));
    }
    
    public void arrojarExcepcion(){
        if(!this.isEmpty()){
            throw this;
        }
    }
    
    @Override
    public int size() {
        return errores.size();
    }

    @Override
    public boolean isEmpty() {
        return errores.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return errores.contains(o);
    }

    @Override
    public Iterator<ErrorGeneral> iterator() {
        return errores.iterator();
    }

    @Override
    public boolean add(ErrorGeneral e) {
        return errores.add(e);
    }

    @Override
    public void add(int index, ErrorGeneral element) {
        this.errores.add(index, element);
    }

    @Override
    public boolean remove(Object o) {
        return errores.remove(o);
    }

    @Override
    public ErrorGeneral remove(int index) {
        return errores.remove(index);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return errores.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends ErrorGeneral> c) {
        return errores.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends ErrorGeneral> c) {
        return errores.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return errores.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return errores.retainAll(c);
    }

    @Override
    public void clear() {
        this.errores.clear();
    }

    @Override
    public ErrorGeneral get(int index) {
        return errores.get(index);
    }

    @Override
    public ErrorGeneral set(int index, ErrorGeneral element) {
        return errores.set(index, element);
    }

    @Override
    public int indexOf(Object o) {
        return errores.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return errores.lastIndexOf(o);
    }

    @Override
    public ListIterator<ErrorGeneral> listIterator() {
        return errores.listIterator();
    }

    @Override
    public ListIterator<ErrorGeneral> listIterator(int index) {
        return errores.listIterator(index);
    }

    @Override
    public List<ErrorGeneral> subList(int fromIndex, int toIndex) {
        return errores.subList(fromIndex, toIndex);
    }

    @Override
    public Object[] toArray() {
       return errores.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
