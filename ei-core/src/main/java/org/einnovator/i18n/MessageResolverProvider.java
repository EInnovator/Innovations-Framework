package org.einnovator.i18n;

import java.util.List;

/**
 * A {@code EnvironmentProvider}.
 *
 * @author Jorge Sim�o, {@code jorge.simao@einnovator.org}
 *
 */
public interface MessageResolverProvider {

	List<MessageResolver> getMessageResolvers();
}
