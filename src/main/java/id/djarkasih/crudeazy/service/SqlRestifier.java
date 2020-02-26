/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package id.djarkasih.crudeazy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.MultiValueMap;

/**
 *
 * @author ahmad
 */
public class SqlRestifier implements Restifier {
    
    private static final Logger logger = LoggerFactory.getLogger(SqlRestifier.class);
    
    private final static String SQL_SELECT_COUNT = "select count(*) from %s";
    private final static String SQL_SELECT_COUNT_WHERE = "select count(*) from %s where %s";
    private final static String SQL_SELECT_FROM = "select %s from %s";
    private final static String SQL_SELECT_FROM_WHERE = "select %s from %s where %s";
    private final static String SQL_INSERT_INTO = "insert into %s (%s) values (%s)";
    private final static String SQL_UPDATE_WHERE = "update %s set %s where %s";
    private final static String SQL_DELETE_WHERE = "delete from %s where %s";
    
    private final static String SQL_ORDER_BY_CLAUSE = "order by %s";
    private final static String SQL_PAGING_OFFSET_CLAUSE = "limit %s offset %s";
    
    private final int defaultPageSize = 10;
    
    
    private final JdbcTemplate jdbcTemplate;
    
    private String buildDistinctFieldsClause(MultiValueMap<String,String> params) {
        
        String clause = null;
        
        List<String> fields = params.get(Restifier.FIELD_KEY);
        if (fields == null) {
            clause = "*";
        } else {
            clause = String.join(",", fields);
            if (params.containsKey(Restifier.IS_DISTINCT_KEY))
                clause = "distinct " + clause;
        }
        
        return clause;
        
    }
    
    private String buildWhereClause(MultiValueMap<String,String> params) {
        
        String clause = null;
        
        List<String> conditions = new ArrayList();
        params.forEach((key,value)->{
            if (! Restifier.RESERVED_WORDS.contains(key)) {
                conditions.add(key + " = '" + value.get(0) + "'");
            }
        });
        
        if (conditions.size() > 0) {
            clause = conditions.get(0);
        }
        
        for (int i=1; i<conditions.size(); i++) {
            
            String condition = conditions.get(i);

            String operator = "and";
            int idx = condition.indexOf(".");
            if (idx != -1) {
                operator = condition.substring(0,idx).toLowerCase();
                if ("and".equals(operator) || "or".equals(operator)) {
                    condition = condition.substring(idx+1,condition.length());
                } else {
                    operator = "and";
                }
            }
            
            clause += " " + operator + " " + condition;
        }
        
        return clause;
        
    }
    
    private Map<String,String> buildColumnsAndValuesClause(Map<String,String> rec) {
        
        Map<String,String> colsVals = null;
        
        List<String> cols = new ArrayList();
        List<String> vals = new ArrayList();
        
        rec.forEach((key,value)->{
            cols.add(key);
            vals.add("'" + value + "'");
        });
        
        String columns = String.join(",", cols);
        String values = String.join(",", vals);
        
        colsVals = new HashMap();
        colsVals.put("columns", columns);
        colsVals.put("values", values);
        
        return colsVals;
        
    }
    
    private String buildSetClause(Map<String,String> rec) {
        
        String clause = null;
        
        List<String> setters = new ArrayList();
        rec.forEach((key,value) -> {
            setters.add(key + "='" + value + "'");
        });
        
        if (setters.size() > 0) {
            clause = String.join(",", setters);
        }
        
        return clause;
        
    }
    
    private String buildOrderByClause(MultiValueMap<String,String> params) {
        
        String clause = null;
        
        List<String> orderBy = params.get(Restifier.ORDER_BY_KEY);
        if (orderBy == null) {
            clause = null;
        } else {
            List <String> orders = new ArrayList();
            orderBy.forEach(val -> {
                
                int idx = val.lastIndexOf(".");
                if (idx == -1) {
                    
                    orders.add(val + " asc");
                    
                } else {
                    
                    String sortingOrder = val.substring(idx+1);
                    if (!((sortingOrder.equalsIgnoreCase("asc") || sortingOrder.equalsIgnoreCase("desc")))) {
                        sortingOrder = "asc";
                    }
                    String col = val.substring(0,idx);
                    orders.add(col + " " + sortingOrder);
                    
                }
                
            });
            
            if (orders.size() > 0) {
                clause = String.format(SQL_ORDER_BY_CLAUSE, String.join(",", orders));
            } else {
                clause = null;
            }
        }
        
        return clause;
        
    }
    
    private String buildPagingClause(MultiValueMap<String,String> params) {
        
        String clause = null;
        
        int pageNo = 1;
        try {
            pageNo = Integer.parseInt(params.get(Restifier.PAGE_NO_KEY).get(0));
        } catch (Exception ex) {           
        }
        
        int pageSize = this.defaultPageSize;
        try {
            pageSize = Integer.parseInt(params.get(Restifier.PAGE_SIZE_KEY).get(0));
        } catch (Exception ex) {
        }
        
        clause = String.format(SQL_PAGING_OFFSET_CLAUSE, pageSize,(pageNo-1)*pageSize);

        return clause;
        
    }

    public SqlRestifier(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long count(String tableName, MultiValueMap<String,String> params) {
        
        String sql = null;
        
        String conditions = this.buildWhereClause(params);
        if (conditions == null) {        
            sql = String.format(SQL_SELECT_COUNT, tableName);
        } else {
            sql = String.format(SQL_SELECT_COUNT_WHERE, tableName, conditions);
        }
        
        logger.info("sql = " + sql);
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        
        return count;
        
    }

    @Override
    public boolean create(String tableName, Map<String, String> rec) {

        Map<String,String> colsValues = this.buildColumnsAndValuesClause(rec);
        
        String sql = String.format(SQL_INSERT_INTO, tableName, colsValues.get("columns"), colsValues.get("values"));

        logger.info("sql = " + sql);
        int numOfRows = jdbcTemplate.update(sql);
                
        return numOfRows == 1;
        
    }

    @Override
    public List<Map<String, Object>> findAll(String tableName, MultiValueMap<String,String> params) {

        String sql;
        
        String fields = this.buildDistinctFieldsClause(params);
        String conditions = this.buildWhereClause(params);
        if (conditions == null) {        
            sql = String.format(SQL_SELECT_FROM, fields, tableName);
        } else {
            sql = String.format(SQL_SELECT_FROM_WHERE, fields, tableName, conditions);
        }
        
        String orders = this.buildOrderByClause(params);
        if (orders != null)
            sql = sql + " " + orders;
        
        sql = sql + " " + this.buildPagingClause(params);
        
        logger.info("sql = " + sql);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        
        return rows;

    }
    
    @Override
    public int update(String tableName, Map<String, String> rec, MultiValueMap<String, String> filters) {
        
        String conditions = this.buildWhereClause(filters);
        String setters = this.buildSetClause(rec);
        
        String sql = String.format(SQL_UPDATE_WHERE, tableName, setters, conditions);
        
        logger.info("sql = " + sql);
        int numOfRows = jdbcTemplate.update(sql);
        
        return numOfRows;
        
    }
    
    @Override
    public int delete(String tableName, MultiValueMap<String, String> filters) {

        String conditions = this.buildWhereClause(filters);
        
        String sql = String.format(SQL_DELETE_WHERE, tableName, conditions);

        logger.info("sql = " + sql);
        int numOfRows = jdbcTemplate.update(sql);
        
        return numOfRows;
        
    }
    
}
