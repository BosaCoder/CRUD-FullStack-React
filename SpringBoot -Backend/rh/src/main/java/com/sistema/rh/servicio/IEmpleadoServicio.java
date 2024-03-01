package com.sistema.rh.servicio;

import com.sistema.rh.modelo.Empleado;

import java.util.List;

public interface IEmpleadoServicio {
    //Listar todos los empleados
    public List<Empleado> listaempleados();

    //buscar empleado por ID
    public Empleado buscarEmpleadoPorId(Integer idEmpleado);

    //Guardar Empleado
    public Empleado guardarEmpleado(Empleado empleado);

    //Eliminar Empleado
    public void eliminarEmpleado(Empleado empleado);




}