/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.model;

/**
 *
 * @author ahmad
 */
public class PagedPayload extends DataPayload {

    private final int pageNo;
    private final int pageSize;

    public PagedPayload(int pageNo, int pageSize) {
        
        super();
        
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }
    
}
