package com.example.droidbarv1.Model.Data;


public class Factura {


    private long id;
    private int table;
    private String start_time;
    private String finish_time;
    private long id_employee_start;
    private long id_employee_finish;
    private float total;

    public Factura(){
        this(0,0,null,"1111-11-11 11:11:11",0,4,0);
    }
    public Factura(long id, int table, String start_time, String finish_time, long id_employee_start, long id_employee_finish, long total) {
        this.id=id;
        this.table = table;
        this.start_time = start_time;
        this.finish_time = finish_time;
        this.id_employee_start = id_employee_start;
        this.id_employee_finish = id_employee_finish;
        this.total = total;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public long getId_employee_start() {
        return id_employee_start;
    }

    public void setId_employee_start(long id_employee_start) {
        this.id_employee_start = id_employee_start;
    }

    public long getId_employee_finish() {
        return id_employee_finish;
    }

    public void setId_employee_finish(long id_employee_finish) {
        this.id_employee_finish = id_employee_finish;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", table=" + table +
                ", id_employee_start=" + id_employee_start +
                ", id_employee_finish=" + id_employee_finish +
                ", total=" + total +
                '}';
    }
}
