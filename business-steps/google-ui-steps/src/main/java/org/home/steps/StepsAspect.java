package org.home.steps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class StepsAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger("STEP");

    @Pointcut("execution(public static void org.home.steps*.*Steps.*(..))")
    void anyStep() {
    }

    @Before("anyStep()")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        String[] methodNameWords = methodName.split("(?=[A-Z])");
        String humanReadableStep = StringUtils.capitalize(StringUtils.join(methodNameWords, " "));
        String message = humanReadableStep + " " + getParameters(signature, joinPoint.getArgs());
        LOGGER.warn(message);
    }

    private String getParameters(MethodSignature signature, Object... args) {
        String[] parameterNames = signature.getParameterNames();
        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < Math.min(parameterNames.length, args.length); i++) {
            params.put(parameterNames[i], String.valueOf(args[i]));
        }
        List<String> entries = params.entrySet().stream().map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.toList());
        return "[" + StringUtils.join(entries, ", ") + "]";
    }

}