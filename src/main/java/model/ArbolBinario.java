package model;

import java.util.*;

public class ArbolBinario {

    private Nodo raiz;

    public Nodo getRaiz() {
        return raiz;
    }

    public void insertar(int valor) {
        raiz = insertarRecursivo(raiz, valor);
    }

    private Nodo insertarRecursivo(Nodo actual, int valor) {
        if (actual == null) return new Nodo(valor);
        if (valor < actual.valor)
            actual.izquierdo = insertarRecursivo(actual.izquierdo, valor);
        else if (valor > actual.valor)
            actual.derecho = insertarRecursivo(actual.derecho, valor);
        return actual;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    public List<Integer> inorden() {
        List<Integer> resultado = new ArrayList<>();
        inordenRec(raiz, resultado);
        return resultado;
    }

    private void inordenRec(Nodo nodo, List<Integer> resultado) {
        if (nodo != null) {
            inordenRec(nodo.izquierdo, resultado);
            resultado.add(nodo.valor);
            inordenRec(nodo.derecho, resultado);
        }
    }

    public List<Integer> preorden() {
        List<Integer> resultado = new ArrayList<>();
        preordenRec(raiz, resultado);
        return resultado;
    }

    private void preordenRec(Nodo nodo, List<Integer> resultado) {
        if (nodo != null) {
            resultado.add(nodo.valor);
            preordenRec(nodo.izquierdo, resultado);
            preordenRec(nodo.derecho, resultado);
        }
    }

    public List<Integer> postorden() {
        List<Integer> resultado = new ArrayList<>();
        postordenRec(raiz, resultado);
        return resultado;
    }

    private void postordenRec(Nodo nodo, List<Integer> resultado) {
        if (nodo != null) {
            postordenRec(nodo.izquierdo, resultado);
            postordenRec(nodo.derecho, resultado);
            resultado.add(nodo.valor);
        }
    }

    public List<Integer> porNiveles() {
        List<Integer> resultado = new ArrayList<>();
        if (raiz == null) return resultado;

        Queue<Nodo> cola = new LinkedList<>();
        cola.add(raiz);

        while (!cola.isEmpty()) {
            Nodo nodo = cola.poll();
            resultado.add(nodo.valor);
            if (nodo.izquierdo != null) cola.add(nodo.izquierdo);
            if (nodo.derecho != null) cola.add(nodo.derecho);
        }

        return resultado;
    }

    public boolean buscar(int valor) {
        return buscarRec(raiz, valor);
    }


    private boolean buscarRec(Nodo nodo, int valor) {
        if (nodo == null) return false;
        if (valor == nodo.valor) return true;
        return valor < nodo.valor
                ? buscarRec(nodo.izquierdo, valor)
                : buscarRec(nodo.derecho, valor);
    }

    public int obtenerPeso() {
        return contarNodos(raiz);
    }

    private int contarNodos(Nodo nodo) {
        if (nodo == null) return 0;
        return 1 + contarNodos(nodo.izquierdo) + contarNodos(nodo.derecho);
    }

    public int obtenerAltura() {
        return altura(raiz);
    }

    private int altura(Nodo nodo) {
        if (nodo == null) return 0;
        return 1 + Math.max(altura(nodo.izquierdo), altura(nodo.derecho));
    }

    public int obtenerNivel(int valor) {
        return obtenerNivelRec(raiz, valor, 0);
    }

    private int obtenerNivelRec(Nodo nodo, int valor, int nivel) {
        if (nodo == null) return -1;
        if (nodo.valor == valor) return nivel;
        int nivelIzq = obtenerNivelRec(nodo.izquierdo, valor, nivel + 1);
        if (nivelIzq != -1) return nivelIzq;
        return obtenerNivelRec(nodo.derecho, valor, nivel + 1);
    }

    public int contarHojas() {
        return contarHojasRec(raiz);
    }

    private int contarHojasRec(Nodo nodo) {
        if (nodo == null) return 0;
        if (nodo.izquierdo == null && nodo.derecho == null) return 1;
        return contarHojasRec(nodo.izquierdo) + contarHojasRec(nodo.derecho);
    }

    public int obtenerMenor() {
        if (raiz == null) throw new NoSuchElementException("Árbol vacío");
        Nodo actual = raiz;
        while (actual.izquierdo != null) actual = actual.izquierdo;
        return actual.valor;
    }

    public int obtenerMayor() {
        if (raiz == null) throw new NoSuchElementException("Árbol vacío");
        Nodo actual = raiz;
        while (actual.derecho != null) actual = actual.derecho;
        return actual.valor;
    }

    public void eliminar(int valor) {
        raiz = eliminarRec(raiz, valor);
    }

    private Nodo eliminarRec(Nodo nodo, int valor) {
        if (nodo == null) return null;

        if (valor < nodo.valor) {
            nodo.izquierdo = eliminarRec(nodo.izquierdo, valor);
        } else if (valor > nodo.valor) {
            nodo.derecho = eliminarRec(nodo.derecho, valor);
        } else {
            // caso 1 y 2: 0 o 1 hijo
            if (nodo.izquierdo == null) return nodo.derecho;
            if (nodo.derecho == null) return nodo.izquierdo;

            // caso 3: 2 hijos
            int menorValor = encontrarMin(nodo.derecho);
            nodo.valor = menorValor;
            nodo.derecho = eliminarRec(nodo.derecho, menorValor);
        }

        return nodo;
    }

    private int encontrarMin(Nodo nodo) {
        while (nodo.izquierdo != null) nodo = nodo.izquierdo;
        return nodo.valor;
    }

    public void borrarArbol() {
        raiz = null;
    }
}