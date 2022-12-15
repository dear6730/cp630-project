package ec.imad.jpa.dao;

import java.util.List;

import javax.ejb.Local;

import ec.imad.jpa.model.Top5Products;

@Local
public interface Top5ProductsDao {
    public void saveModel(Top5Products top5Products);
    public List<Top5Products> getAll();
}