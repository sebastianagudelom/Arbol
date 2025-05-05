package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import model.ArbolBinario;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class ArbolController {
    private final ArbolBinario arbol = new ArbolBinario();

    @FXML
    private Pane lienzo;

    @FXML
    private TextField entrada;

    @FXML
    private TextArea salida;

    @FXML
    public void insertar(ActionEvent event) {
        try {
            int valor = Integer.parseInt(entrada.getText());
            arbol.insertar(valor);
            salida.setText("Nodo insertado: " + valor);
            dibujarArbol();
        } catch (NumberFormatException ex) {
            salida.setText("Valor inválido.");
        }
    }

    @FXML
    public void inorden(ActionEvent event) {
        salida.setText("Inorden: " + arbol.inorden());
    }

    @FXML
    public void preorden(ActionEvent event) {
        salida.setText("Preorden: " + arbol.preorden());
    }

    @FXML
    public void postorden(ActionEvent event) {
        salida.setText("Postorden: " + arbol.postorden());
    }

    @FXML
    public void porNiveles(ActionEvent event) {
        salida.setText("Por niveles: " + arbol.porNiveles());
    }

    @FXML
    public void buscar(ActionEvent event) {
        try {
            int valor = Integer.parseInt(entrada.getText());
            boolean encontrado = arbol.buscar(valor);
            salida.setText(encontrado ? "Valor encontrado" : "Valor no encontrado");
        } catch (NumberFormatException ex) {
            salida.setText("Valor inválido.");
        }
    }

    @FXML
    public void eliminar(ActionEvent event) {
        try {
            int valor = Integer.parseInt(entrada.getText());
            arbol.eliminar(valor);
            salida.setText("Nodo eliminado: " + valor);
            dibujarArbol();
        } catch (NumberFormatException ex) {
            salida.setText("Valor inválido.");
        }
    }

    @FXML
    public void peso(ActionEvent event) {
        salida.setText("Peso: " + arbol.obtenerPeso());
    }

    @FXML
    public void altura(ActionEvent event) {
        salida.setText("Altura: " + arbol.obtenerAltura());
    }

    @FXML
    public void nivel(ActionEvent event) {
        try {
            int valor = Integer.parseInt(entrada.getText());
            int nivel = arbol.obtenerNivel(valor);
            salida.setText(nivel != -1 ? "Nivel de " + valor + ": " + nivel : "Valor no encontrado");
        } catch (NumberFormatException ex) {
            salida.setText("Valor inválido.");
        }
    }

    @FXML
    public void contarHojas(ActionEvent event) {
        salida.setText("Hojas: " + arbol.contarHojas());
    }

    @FXML
    public void menor(ActionEvent event) {
        try {
            salida.setText("Menor: " + arbol.obtenerMenor());
        } catch (Exception ex) {
            salida.setText("Árbol vacío.");
        }
    }

    @FXML
    public void mayor(ActionEvent event) {
        try {
            salida.setText("Mayor: " + arbol.obtenerMayor());
        } catch (Exception ex) {
            salida.setText("Árbol vacío.");
        }
    }

    @FXML
    public void borrarArbol(ActionEvent event) {
        arbol.borrarArbol();
        salida.setText("Árbol borrado.");
        dibujarArbol();
    }

    private void dibujarArbol() {
        double ancho = lienzo.getWidth();
        double alto = lienzo.getHeight();

        // Ajusta el tamaño del lienzo si es necesario
        if (ancho == 0) ancho = 800;  // Ajustar tamaño mínimo
        if (alto == 0) alto = 600;  // Ajustar tamaño mínimo

        lienzo.setPrefWidth(ancho);
        lienzo.setPrefHeight(alto);
        lienzo.getChildren().clear();

        if (!arbol.estaVacio()) {
            dibujarNodo(arbol.getRaiz(), ancho / 2, 30, ancho / 4);
        }
    }

    private void dibujarNodo(model.Nodo nodo, double x, double y, double offset) {
        if (nodo == null) return;

        Circle circulo = new Circle(x, y, 20);
        circulo.setStyle("-fx-fill: lightblue; -fx-stroke: black;");
        Text texto = new Text(x - 7, y + 5, String.valueOf(nodo.valor));
        texto.setStyle("-fx-font-weight: bold;");

        lienzo.getChildren().addAll(circulo, texto);

        // Reducir la distancia entre nodos para evitar que las ramas se vean demasiado grandes
        double nuevoOffset = Math.max(offset / 1.5, 30);  // Esto ajusta el espacio entre los nodos

        if (nodo.izquierdo != null) {
            double xIzq = x - nuevoOffset;
            double yHijo = y + 60;
            Line linea = new Line(x, y, xIzq, yHijo);
            lienzo.getChildren().add(linea);
            dibujarNodo(nodo.izquierdo, xIzq, yHijo, nuevoOffset);
        }

        if (nodo.derecho != null) {
            double xDer = x + nuevoOffset;
            double yHijo = y + 60;
            Line linea = new Line(x, y, xDer, yHijo);
            lienzo.getChildren().add(linea);
            dibujarNodo(nodo.derecho, xDer, yHijo, nuevoOffset);
        }
    }
}