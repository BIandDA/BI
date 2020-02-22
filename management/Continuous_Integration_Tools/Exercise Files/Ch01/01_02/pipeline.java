import com.atlassian.bamboo.specs.api.BambooSpec;
import com.atlassian.bamboo.specs.api.builders.BambooKey;
import com.atlassian.bamboo.specs.api.builders.BambooOid;
import com.atlassian.bamboo.specs.api.builders.permission.PermissionType;
import com.atlassian.bamboo.specs.api.builders.permission.Permissions;
import com.atlassian.bamboo.specs.api.builders.permission.PlanPermissions;
import com.atlassian.bamboo.specs.api.builders.plan.Job;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.builders.plan.PlanIdentifier;
import com.atlassian.bamboo.specs.api.builders.plan.Stage;
import com.atlassian.bamboo.specs.api.builders.plan.branches.BranchCleanup;
import com.atlassian.bamboo.specs.api.builders.plan.branches.PlanBranchManagement;
import com.atlassian.bamboo.specs.api.builders.plan.configuration.ConcurrentBuilds;
import com.atlassian.bamboo.specs.api.builders.project.Project;
import com.atlassian.bamboo.specs.builders.task.CheckoutItem;
import com.atlassian.bamboo.specs.builders.task.ScriptTask;
import com.atlassian.bamboo.specs.builders.task.VcsCheckoutTask;
import com.atlassian.bamboo.specs.builders.trigger.RepositoryPollingTrigger;
import com.atlassian.bamboo.specs.util.BambooServer;

@BambooSpec
public class PlanSpec {

    public Plan plan() {
        final Plan plan = new Plan(
                new Project().oid(new BambooOid("fqxfsshgzj7l")).key(new BambooKey("BAM")).name("bamboo-demo"),
                "pipeline", new BambooKey("BAM")).oid(new BambooOid("fqnql7495qtd"))
                        .pluginConfigurations(new ConcurrentBuilds())
                        .stages(new Stage("Check").description("Run linters and unit tests.").jobs(
                                new Job("Lint", new BambooKey("LINT")).description("Run linters.").tasks(
                                        new VcsCheckoutTask().description("Checkout Default Repository").checkoutItems(
                                                new CheckoutItem().defaultRepository()),
                                        new ScriptTask().description("Run flake8 and pylint").inlineBody(
                                                "#!/bin/bash\npython3 -m virtualenv venv\nsource venv/bin/activate\npip install --upgrade --requirement requirements.txt\nmake lint")),
                                new Job("Unit Tests", new BambooKey("TEST")).description("Run unit tests.").tasks(
                                        new VcsCheckoutTask().description("Checkout Default Repository")
                                                .checkoutItems(new CheckoutItem().defaultRepository()),
                                        new ScriptTask().description("Run unit tests").inlineBody(
                                                "#!/bin/bash\npython3 -m virtualenv venv\nsource venv/bin/activate\npip install --upgrade --requirement requirements.txt\nmake unittest"))),
                                new Stage("Build").description("Upload a new version of the application")
                                        .jobs(new Job("Upload New Version", new BambooKey("BUIL"))
                                                .description("Upload a new version of the application")
                                                .tasks(new VcsCheckoutTask().description("Checkout Default Repository")
                                                        .checkoutItems(new CheckoutItem().defaultRepository()),
                                                        new ScriptTask().description("Run upload script").inlineBody(
                                                                "#!/bin/bash\npython3 -m virtualenv venv\nsource venv/bin/activate\npip install --upgrade --requirement requirements.txt\n./upload-new-version.sh")
                                                                .environmentVariables(
                                                                        "AWS_ACCESS_KEY_ID=${bamboo.secretAWS_ACCESS_KEY_ID} AWS_DEFAULT_REGION=${bamboo.AWS_DEFAULT_REGION} AWS_SECRET_ACCESS_KEY=${bamboo.secretAWS_SECRET_ACCESS_KEY}"))),
                                new Stage("Deploy Staging").description("Deploy new version to the staging environment")
                                        .jobs(new Job("Deploy Staging", new BambooKey("DEP"))
                                                .description("Run a deployment for staging environment")
                                                .tasks(new VcsCheckoutTask().description("Checkout Default Repository")
                                                        .checkoutItems(new CheckoutItem().defaultRepository()),
                                                        new ScriptTask().description("Run deploy script").inlineBody(
                                                                "#!/bin/bash\npython3 -m virtualenv venv\nsource venv/bin/activate\npip install --upgrade --requirement requirements.txt\n./deploy-new-version.sh $1")
                                                                .argument("staging").environmentVariables(
                                                                        "AWS_ACCESS_KEY_ID=${bamboo.secretAWS_ACCESS_KEY_ID} AWS_DEFAULT_REGION=${bamboo.AWS_DEFAULT_REGION} AWS_SECRET_ACCESS_KEY=${bamboo.secretAWS_SECRET_ACCESS_KEY}"))),
                                new Stage("Test Staging").description("Test the staging environment")
                                        .jobs(new Job("Test Staging", new BambooKey("TESTER"))
                                                .description("Run tests on staging environment")
                                                .tasks(new VcsCheckoutTask().description("Checkout Default Repository")
                                                        .checkoutItems(new CheckoutItem().defaultRepository()),
                                                        new ScriptTask().description("Run test script")
                                                                .inlineBody("#!/bin/bash\n./test-environment.sh $1")
                                                                .argument("staging"))),
                                new Stage("Deploy Production")
                                        .description("Deploy new version to the production environment")
                                        .jobs(new Job("Deploy Production", new BambooKey("DEPLOY2"))
                                                .description("Run a deployment for production environment")
                                                .tasks(new VcsCheckoutTask().description("Checkout Default Repository")
                                                        .checkoutItems(new CheckoutItem().defaultRepository()),
                                                        new ScriptTask().description("Run deploy script").inlineBody(
                                                                "#!/bin/bash\npython3 -m virtualenv venv\nsource venv/bin/activate\npip install --upgrade --requirement requirements.txt\n./deploy-new-version.sh $1")
                                                                .argument("production").environmentVariables(
                                                                        "AWS_ACCESS_KEY_ID=${bamboo.secretAWS_ACCESS_KEY_ID} AWS_DEFAULT_REGION=${bamboo.AWS_DEFAULT_REGION} AWS_SECRET_ACCESS_KEY=${bamboo.secretAWS_SECRET_ACCESS_KEY}"))),
                                new Stage("Test Production").description("Test the production environment")
                                        .jobs(new Job("Test Production", new BambooKey("TP")).tasks(
                                                new VcsCheckoutTask().description("Checkout Default Repository")
                                                        .checkoutItems(new CheckoutItem().defaultRepository()),
                                                new ScriptTask().description("Run test script")
                                                        .inlineBody("#!/bin/bash\n./test-environment.sh $1")
                                                        .argument("production"))))
                        .linkedRepositories("bamboo-demo")

                        .triggers(new RepositoryPollingTrigger()).planBranchManagement(
                                new PlanBranchManagement().delete(new BranchCleanup()).notificationForCommitters());
        return plan;
    }

    public PlanPermissions planPermission() {
        final PlanPermissions planPermission = new PlanPermissions(new PlanIdentifier("BAM", "BAM"))
                .permissions(new Permissions()
                        .userPermissions("automate6500", PermissionType.EDIT, PermissionType.VIEW, PermissionType.ADMIN,
                                PermissionType.CLONE, PermissionType.BUILD)
                        .loggedInUserPermissions(PermissionType.VIEW).anonymousUserPermissionView());
        return planPermission;
    }

    public static void main(String... argv) {
        // By default credentials are read from the '.credentials' file.
        BambooServer bambooServer = new BambooServer("http://178.128.7.18:8085");
        final PlanSpec planSpec = new PlanSpec();

        final Plan plan = planSpec.plan();
        bambooServer.publish(plan);

        final PlanPermissions planPermission = planSpec.planPermission();
        bambooServer.publish(planPermission);
    }
}
