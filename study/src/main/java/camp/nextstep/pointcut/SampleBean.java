package camp.nextstep.pointcut;

public class SampleBean {

    public String dynamicPointcut(int x) {
        return "Invoked dynamicPointcut(" + x + ")";
    }

    @CustomAnnotation
    public String annotationPointcut() {
        return "Invoked annotationPointcut()";
    }
}
