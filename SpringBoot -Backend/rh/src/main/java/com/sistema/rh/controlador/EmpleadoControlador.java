package com.sistema.rh.controlador;

import com.sistema.rh.excepcion.RecursoNoEncontradoExcepcion;
import com.sistema.rh.modelo.Empleado;
import com.sistema.rh.servicio.IEmpleadoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
// http://localhost:8080/rh-app/
@RequestMapping("rh-app")
@CrossOrigin(origins = "http://localhost:3000")
public class EmpleadoControlador {
    private static final Logger logger =
            LoggerFactory.getLogger(EmpleadoControlador.class);

    @Autowired
    private IEmpleadoServicio empleadoServicio;

    // http://localhost:8080/rh-app/empleados
    @GetMapping("/empleados")
    public List<Empleado> obtenerEmpleados() {
        logger.info("Obteniendo empleados");
        var empleados = empleadoServicio.listaempleados();
        empleados.forEach(empleado -> logger.info(empleado.toString()));
        return empleados;
    }

    @PostMapping("/empleados")
    public Empleado agregarEmpleado(@RequestBody Empleado empleado) {
        logger.info("Guardando empleado" + empleado);
        return empleadoServicio.guardarEmpleado(empleado);
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<Empleado> obtenerEmpleadoPorId(@PathVariable(value = "id") Integer id) {
        logger.info("Obteniendo empleado por id: " + id);
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if (empleado == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontró el empleado con id: " + id);
        }
        return ResponseEntity.ok(empleado);
    }

    @PutMapping("/empleados/{id}")
    public ResponseEntity<Empleado> actualizarEmpleado(@PathVariable Integer id,
                                                       @RequestBody Empleado empleadoRecibodo){
        logger.info("Actualizando empleado en Put con id: " + id);
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if (empleado == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontró el empleado con id: " + id);
        }
        empleado.setNombre(empleadoRecibodo.getNombre());
        empleado.setDepartamento(empleadoRecibodo.getDepartamento());
        empleado.setSueldo(empleadoRecibodo.getSueldo());
        empleadoServicio.guardarEmpleado(empleado);
        return ResponseEntity.ok(empleado);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<Map<String, Boolean>>
            eliminarEmpleado(@PathVariable Integer id){
        logger.info("Eliminando empleado en Delete con id: " + id);
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(id);
        if (empleado == null) {
            throw new RecursoNoEncontradoExcepcion("No se encontró el empleado con id: " + id);
        }
        empleadoServicio.eliminarEmpleado(empleado);
        //JSON {"Eliminado": true}
        return ResponseEntity.ok().body(new HashMap<String, Boolean>() {{
            put("Eliminado", Boolean.TRUE);
        }});
    }
}
