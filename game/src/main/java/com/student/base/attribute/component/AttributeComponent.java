package com.student.base.attribute.component;

import com.student.base.component.model.Ability;
import com.student.base.attribute.container.AttributeContainer;
import com.student.base.component.Component;
import com.student.base.model.GameObject;

/**
 * @author : luoyong
 * @date : 2021-03-20 22:57
 **/
public interface AttributeComponent<E extends GameObject> extends Component<E>, AttributeContainer, Ability {
}
