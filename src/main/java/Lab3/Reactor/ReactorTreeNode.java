package Lab3.Reactor;

import javax.swing.tree.*;

public class ReactorTreeNode extends DefaultMutableTreeNode {


    public ReactorTreeNode(Reactor reactor) {
        super(reactor.type);

        add(new DefaultMutableTreeNode("Источник: " + reactor.source));
        add(new DefaultMutableTreeNode("Выгорание: " + reactor.burnup));
        add(new DefaultMutableTreeNode("КПД: " + reactor.kpd));
        add(new DefaultMutableTreeNode("Обогащение: " + reactor.enrichment));
        add(new DefaultMutableTreeNode("Тепловая мощность: " + reactor.thermal_capacity));
        add(new DefaultMutableTreeNode("Электрическая мощность: " + reactor.electrical_capacity));
        add(new DefaultMutableTreeNode("Срок службы: " + reactor.life_time));
        add(new DefaultMutableTreeNode("Первая загрузка: " + reactor.first_load));
    }


    public static ReactorTreeNode createReactorNode(Reactor reactor) {

        return new ReactorTreeNode(reactor);
    }
}
