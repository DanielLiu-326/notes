# 寄存器

## 数据寄存器

ax,bx,cx,dx可以都可以分为两个八位寄存器

ax = ah + al

bx = bh + bl

cx =  ch + cl

dx = dh + dl

al与ah不会互相影响。

## 地址寄存器

## 段地址寄存器

ds

es

ss

cs指令段地址寄存器

## 偏移地址寄存器

sp

bp

si

di

ip指令偏移地址寄存器

bx

段地址*16+偏移地址 =物理地址

