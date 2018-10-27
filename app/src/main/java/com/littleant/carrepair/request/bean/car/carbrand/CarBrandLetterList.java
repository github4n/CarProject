package com.littleant.carrepair.request.bean.car.carbrand;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 汽车品牌按字母排序
 */
public class CarBrandLetterList implements Serializable {
    private List<CarBaseBean> A;
    private List<CarBaseBean> B;
    private List<CarBaseBean> C;
    private List<CarBaseBean> D;
    private List<CarBaseBean> E;
    private List<CarBaseBean> F;
    private List<CarBaseBean> G;
    private List<CarBaseBean> H;
    private List<CarBaseBean> I;
    private List<CarBaseBean> J;
    private List<CarBaseBean> K;
    private List<CarBaseBean> L;
    private List<CarBaseBean> M;
    private List<CarBaseBean> N;
    private List<CarBaseBean> O;
    private List<CarBaseBean> P;
    private List<CarBaseBean> Q;
    private List<CarBaseBean> R;
    private List<CarBaseBean> S;
    private List<CarBaseBean> T;
    private List<CarBaseBean> U;
    private List<CarBaseBean> V;
    private List<CarBaseBean> W;
    private List<CarBaseBean> X;
    private List<CarBaseBean> Y;
    private List<CarBaseBean> Z;

    public List<CarBaseBean> getA() {
        return A;
    }

    public void setA(List<CarBaseBean> a) {
        A = a;
    }

    public List<CarBaseBean> getB() {
        return B;
    }

    public void setB(List<CarBaseBean> b) {
        B = b;
    }

    public List<CarBaseBean> getC() {
        return C;
    }

    public void setC(List<CarBaseBean> c) {
        C = c;
    }

    public List<CarBaseBean> getD() {
        return D;
    }

    public void setD(List<CarBaseBean> d) {
        D = d;
    }

    public List<CarBaseBean> getE() {
        return E;
    }

    public void setE(List<CarBaseBean> e) {
        E = e;
    }

    public List<CarBaseBean> getF() {
        return F;
    }

    public void setF(List<CarBaseBean> f) {
        F = f;
    }

    public List<CarBaseBean> getG() {
        return G;
    }

    public void setG(List<CarBaseBean> g) {
        G = g;
    }

    public List<CarBaseBean> getH() {
        return H;
    }

    public void setH(List<CarBaseBean> h) {
        H = h;
    }

    public List<CarBaseBean> getI() {
        return I;
    }

    public void setI(List<CarBaseBean> i) {
        I = i;
    }

    public List<CarBaseBean> getJ() {
        return J;
    }

    public void setJ(List<CarBaseBean> j) {
        J = j;
    }

    public List<CarBaseBean> getK() {
        return K;
    }

    public void setK(List<CarBaseBean> k) {
        K = k;
    }

    public List<CarBaseBean> getL() {
        return L;
    }

    public void setL(List<CarBaseBean> l) {
        L = l;
    }

    public List<CarBaseBean> getM() {
        return M;
    }

    public void setM(List<CarBaseBean> m) {
        M = m;
    }

    public List<CarBaseBean> getN() {
        return N;
    }

    public void setN(List<CarBaseBean> n) {
        N = n;
    }

    public List<CarBaseBean> getO() {
        return O;
    }

    public void setO(List<CarBaseBean> o) {
        O = o;
    }

    public List<CarBaseBean> getP() {
        return P;
    }

    public void setP(List<CarBaseBean> p) {
        P = p;
    }

    public List<CarBaseBean> getQ() {
        return Q;
    }

    public void setQ(List<CarBaseBean> q) {
        Q = q;
    }

    public List<CarBaseBean> getR() {
        return R;
    }

    public void setR(List<CarBaseBean> r) {
        R = r;
    }

    public List<CarBaseBean> getS() {
        return S;
    }

    public void setS(List<CarBaseBean> s) {
        S = s;
    }

    public List<CarBaseBean> getT() {
        return T;
    }

    public void setT(List<CarBaseBean> t) {
        T = t;
    }

    public List<CarBaseBean> getU() {
        return U;
    }

    public void setU(List<CarBaseBean> u) {
        U = u;
    }

    public List<CarBaseBean> getV() {
        return V;
    }

    public void setV(List<CarBaseBean> v) {
        V = v;
    }

    public List<CarBaseBean> getW() {
        return W;
    }

    public void setW(List<CarBaseBean> w) {
        W = w;
    }

    public List<CarBaseBean> getX() {
        return X;
    }

    public void setX(List<CarBaseBean> x) {
        X = x;
    }

    public List<CarBaseBean> getY() {
        return Y;
    }

    public void setY(List<CarBaseBean> y) {
        Y = y;
    }

    public List<CarBaseBean> getZ() {
        return Z;
    }

    public void setZ(List<CarBaseBean> z) {
        Z = z;
    }

    private ArrayList<List<CarBaseBean>> list;
    private LinkedHashMap<String, List<CarBaseBean>> hashMap;

    public ArrayList<List<CarBaseBean>> toArrayList() {
        if (list == null) {
            list = new ArrayList<>();
            list.add(A);
            list.add(B);
            list.add(C);
            list.add(D);
            list.add(E);
            list.add(F);
            list.add(G);
            list.add(H);
            list.add(I);
            list.add(J);
            list.add(K);
            list.add(L);
            list.add(M);
            list.add(N);
            list.add(O);
            list.add(P);
            list.add(Q);
            list.add(R);
            list.add(S);
            list.add(T);
            list.add(U);
            list.add(V);
            list.add(W);
            list.add(X);
            list.add(Y);
            list.add(Z);
        }
        return list;
    }

    public LinkedHashMap<String, List<CarBaseBean>> toMap() {
        if(hashMap == null) {
            hashMap = new LinkedHashMap<String, List<CarBaseBean>>();
            hashMap.put("A", A);
            hashMap.put("B", B);
            hashMap.put("C", C);
            hashMap.put("D", D);
            hashMap.put("E", E);
            hashMap.put("F", F);
            hashMap.put("G", G);
            hashMap.put("H", H);
            hashMap.put("I", I);
            hashMap.put("J", J);
            hashMap.put("K", K);
            hashMap.put("L", L);
            hashMap.put("M", M);
            hashMap.put("N", N);
            hashMap.put("O", O);
            hashMap.put("P", P);
            hashMap.put("Q", Q);
            hashMap.put("R", R);
            hashMap.put("S", S);
            hashMap.put("T", T);
            hashMap.put("U", U);
            hashMap.put("V", V);
            hashMap.put("W", W);
            hashMap.put("X", X);
            hashMap.put("Y", Y);
            hashMap.put("Z", Z);
        }
        return hashMap;
    }
}
