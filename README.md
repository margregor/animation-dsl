```bash
Animator [opcje] plik
```

### Opcje
- `-w, --width <liczba>` - szerokość okna (domyślnie 800)
- `-h, --height <liczba>` - wysokość okna (domyślnie 600)
- `-f, --frames <liczba>` - docelowa liczba klatek na sekundę (FPS) (domyślnie 60)

### Przykład użycia

```
x = linear<60>(0, windowWidth);
"image.png" translate(x, windowHeight/2);
```

```bash
java -jar Animator.jar animacja.anim
```

- Po zakończeniu animacji wyświetlany jest napis "Fin" na środku ekranu
- Aktualne wartości zmiennych są wyświetlane w lewym górnym rogu ekranu

Dostępne interpolatory (oparte na https://easings.net):
- linear
- easeInSine
- easeOutSine
- easeInOutSine
- easeInQuad
- easeOutQuad
- easeInOutQuad
- easeInCubic
- easeOutCubic
- easeInOutCubic
- easeInQuart
- easeOutQuart
- easeInOutQuart
- easeInQuint
- easeOutQuint
- easeInOutQuint
- easeInExpo
- easeOutExpo
- easeInOutExpo
- easeInCirc
- easeOutCirc
- easeInOutCirc
- easeInBack
- easeOutBack
- easeInOutBack
- easeInElastic
- easeOutElastic
- easeInOutElastic
- easeInBounce
- easeOutBounce
- easeInOutBounce

Dostępne transformacje (oparte na https://www.w3.org/TR/SVGTiny12/coords.html#TransformList):
- translate
- scale
- rotate
- skewX
- skewY
- matrix
