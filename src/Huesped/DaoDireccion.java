package Huesped;

    public class DaoDireccion implements DaoDireccionInterfaz {
        @Override
        public boolean CrearDireccion(DtoDireccion dto) {
            // Lógica para crear una dirección en la base de datos
            return true;
        }

        @Override
        public boolean ModificarDireccion(int idDireccion) {
            // Lógica para modificar una dirección en la base de datos
            return true;
        }

        @Override
        public boolean EliminarDireccion(int idDireccion) {
            // Lógica para eliminar una dirección en la base de datos
            return true;
        }

        @Override
        public DtoDireccion ObtenerDireccion(int idDireccion) {
            // Lógica para obtener una dirección de la base de datos
            return null;
        }
    }


//queremos tener diferentes metodos para devolver por ej una lista de dto?
//tenemos que controlar excepciones